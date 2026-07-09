import { DataSource } from "typeorm";

export abstract class AbstractSeeder {
  constructor(protected readonly datasource: DataSource) {}

  abstract run(): Promise<void>;
}
