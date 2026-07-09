import { DataSource } from "typeorm";
import { Dummy } from "@nestapp/entities";
import { AbstractSeeder } from "./abstract-seeder";
import { DUMMY_1, DUMMY_2, DUMMY_3 } from "../../mocks";

export class DummySeeder extends AbstractSeeder {
  constructor(datasource: DataSource) {
    super(datasource);
  }

  async run() {
    const repository = this.datasource.getRepository(Dummy);
    await repository.save([DUMMY_1, DUMMY_2, DUMMY_3]);
  }
}
