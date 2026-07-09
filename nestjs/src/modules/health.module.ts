import { Module } from "@nestjs/common";
import { TypeOrmModule } from "@nestjs/typeorm";

import { HealthController } from "@nestapp/rest/controller";
import { HealthService } from "@nestapp/services";
import { Dummy } from "@nestapp/entities";

@Module({
  imports: [TypeOrmModule.forFeature([Dummy])],
  providers: [HealthService],
  controllers: [HealthController],
})
export class HealthModule {}
