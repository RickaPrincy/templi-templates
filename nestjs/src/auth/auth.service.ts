import * as bcrypt from "bcrypt";
import { ForbiddenException, Injectable } from "@nestjs/common";
import { JwtService } from "@nestjs/jwt";

import { User } from "src/model";
import { UserService } from "src/service";
import { Whoami, JwtPayload, SigninPayload, SignupPayload } from "./model";
import { UserMapper } from "src/rest/mapper";

@Injectable()
export class AuthService {
  constructor(
    private readonly userService: UserService,
    private readonly userMapper: UserMapper,
    private readonly jwtService: JwtService
  ) {}

  async whoami(token: string, user: User): Promise<Whoami> {
    const restUser = await this.userMapper.toRest(user);
    return { ...restUser, token };
  }

  async signup(payload: SignupPayload): Promise<Whoami> {
    const user = await this.userMapper.createToDomain(payload);
    const savedUser = await this.userService.createUser(user);
    return this.domainUserToWhoami(savedUser);
  }

  async signin({
    username: candidateUsername,
    password: candidatePassword,
  }: SigninPayload): Promise<Whoami> {
    const user = await this.userService.findByUsername(candidateUsername);
    if (!user) {
      throw new ForbiddenException("Invalid credentials");
    }

    const isCorrectPassword = await bcrypt.compare(
      candidatePassword,
      user.password
    );
    if (!isCorrectPassword) {
      throw new ForbiddenException("Invalid credentials");
    }

    return this.domainUserToWhoami(user);
  }

  async domainUserToWhoami(user: User): Promise<Whoami> {
    const jwtPayload: JwtPayload = {
      id: user.id,
      username: user.username,
    };
    const token = this.jwtService.sign(jwtPayload);
    return this.whoami(token, user);
  }
}
