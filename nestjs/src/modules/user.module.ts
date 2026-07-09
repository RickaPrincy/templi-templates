import { Module } from "@nestjs/common";
import { TypeOrmModule } from "@nestjs/typeorm";
import { UserController } from "@nestapp/rest/controller";
import { UserService } from "@nestapp/services";
import { UserMapper } from "@nestapp/rest/mapper";
import { User } from "@nestapp/entities";

@Module({
  imports: [TypeOrmModule.forFeature([User])],
  controllers: [UserController],
  providers: [UserService, UserMapper],
  exports: [UserService, UserMapper],
})
export class UserModule {}
