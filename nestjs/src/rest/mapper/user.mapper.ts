import { Injectable } from "@nestjs/common";
import { User } from "../model";
import { User as DomainUser } from "src/model";
import { Repository } from "typeorm";
import { InjectRepository } from "@nestjs/typeorm";

@Injectable()
export class UserMapper {
  constructor(
    @InjectRepository(DomainUser)
    private readonly userRepository: Repository<DomainUser>
  ) {}

  async toRest(user: DomainUser): Promise<User> {
    const { deletedAt: _deletedAt, password: _password, ...restUser } = user;
    return restUser;
  }

  async createToDomain(createUser: User): Promise<DomainUser> {
    return this.userRepository.create(createUser);
  }
}
