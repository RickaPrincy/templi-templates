import { Repository } from "typeorm";
import { Injectable } from "@nestjs/common";
import { InjectRepository } from "@nestjs/typeorm";

import { Dummy } from "src/model/dummy.entity";
import { PaginationParams } from "src/rest/decorator";
import { createPagination } from "./utils/create-pagination";

@Injectable()
export class HealthService {
  constructor(
    @InjectRepository(Dummy) private readonly repository: Repository<Dummy>
  ) {}

  async getDummies(pagination: PaginationParams) {
    return this.repository.find(createPagination(pagination));
  }
}
