import { Controller, Get, Query } from "@nestjs/common";
import { ApiTags } from "@nestjs/swagger";
import { Authenticated } from "src/auth/decorator";
import { Role } from "src/model";
import { User } from "../model";
import { UserService } from "src/service";
import {
  ApiCriteria,
  ApiPagination,
  ApiRequiredSpec,
} from "../swagger/decorator";
import { Pagination, PaginationParams } from "../decorator";
import { ILike } from "typeorm";
import { UserMapper } from "../mapper";

@Controller()
@ApiTags("User")
export class UserController {
  constructor(
    private readonly userService: UserService,
    private readonly userMapper: UserMapper
  ) {}

  @Get("/users")
  @ApiPagination()
  @Authenticated({ roles: [Role.ADMIN] })
  @ApiCriteria({ name: "username", type: "string" })
  @ApiRequiredSpec({ operationId: "getUsers", type: [User] })
  async getUsers(
    @Pagination() pagination: PaginationParams,
    @Query("username") username?: string
  ) {
    const users = await this.userService.findAll(pagination, {
      username: username ? ILike(`%${username}%`) : undefined,
    });

    return Promise.all(users.map((user) => this.userMapper.toRest(user)));
  }
}
