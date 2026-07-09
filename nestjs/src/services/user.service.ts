import { Repository } from "typeorm";
import { BadRequestException, Injectable } from "@nestjs/common";
import { InjectRepository } from "@nestjs/typeorm";

import { User } from "@nestapp/entities";
import { Criteria } from "./common/criteria";
import { PaginationParams } from "@nestapp/rest/decorator";
import { UPDATED_AT_CREATED_AT_ORDER_BY } from "./common/default-order-by";
import { findByCriteria } from "./common/find-by-criteria";

@Injectable()
export class UserService {
  constructor(
    @InjectRepository(User) private readonly repository: Repository<User>
  ) {}

  async findAll(pagination: PaginationParams, criteria: Criteria<User>) {
    return findByCriteria<User>({
      repository: this.repository,
      criteria,
      pagination,
      order: UPDATED_AT_CREATED_AT_ORDER_BY,
    });
  }

  async findById(id: string) {
    return this.repository.findOneBy({ id });
  }

  async findByUsername(username: string) {
    return this.repository.findOneBy({ username });
  }

  async createUser(user: User): Promise<User> {
    if (await this.findById(user.id)) {
      throw new BadRequestException("User with the given id already exist");
    }

    const createdUser = this.repository.create(user); // just to make sure that the password will be hashed

    return await this.repository.save(createdUser);
  }
}
