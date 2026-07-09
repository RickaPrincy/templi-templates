import { Module } from "@nestjs/common";
import { ConfigModule } from "@nestjs/config";

import { AuthModule } from "./auth";
import { DatabaseModule } from "./modules/database";
import { HealthModule, UserModule } from "./modules";

@Module({
  imports: [
    ConfigModule.forRoot({
      isGlobal: true,
    }),
    DatabaseModule,
    HealthModule,
    UserModule,
    AuthModule,
  ],
})
export class AppModule {}
