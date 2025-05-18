import { Module } from "@nestjs/common";
import { TypeOrmModule } from "@nestjs/typeorm";

import { HealthController } from "src/rest/controller";
import { HealthService } from "src/service";
import { Dummy } from "src/model";

@Module({
  imports: [TypeOrmModule.forFeature([Dummy])],
  providers: [HealthService],
  controllers: [HealthController],
})
export class HealthModule {}
