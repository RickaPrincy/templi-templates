import { DataSource } from "typeorm";
import { User } from "@nestapp/entities";
import { AbstractSeeder } from "./abstract-seeder";
import { JANE, JOHN } from "../../mocks";

export class UserSeeder extends AbstractSeeder {
  constructor(datasource: DataSource) {
    super(datasource);
  }

  async run() {
    const repository = this.datasource.getRepository(User);
    await repository.save([JOHN, JANE]);
  }
}
