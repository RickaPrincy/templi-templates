import { Global, Module } from "@nestjs/common";
import { PassportModule } from "@nestjs/passport";
import { JwtModule } from "@nestjs/jwt";
import { TypeOrmModule } from "@nestjs/typeorm";
import { ConfigService } from "@nestjs/config";

import { AuthService } from "./auth.service";
import { AuthController } from "./auth.controller";
import { JwtStrategy } from "./startegy";
import { JwtAuthGuard, RoleGuard, SelfMatcherGuard } from "./guards";
import { User } from "src/model";
import { UserService } from "src/service";
import { UserMapper } from "src/rest/mapper";

@Global()
@Module({
  imports: [
    PassportModule,
    JwtModule.registerAsync({
      inject: [ConfigService],
      useFactory: async (configService: ConfigService) => {
        return {
          secret: configService.get("JWT_SECRET"),
          signOptions: {
            // 5m | 5h | 5d
            expiresIn: configService.get("JWT_EXPIRATION_TIME"),
          },
        };
      },
    }),
    TypeOrmModule.forFeature([User]),
  ],
  providers: [
    JwtAuthGuard,
    JwtStrategy,
    AuthService,
    UserMapper,
    UserService,
    RoleGuard,
    SelfMatcherGuard,
  ],
  controllers: [AuthController],
})
export class AuthModule {}
