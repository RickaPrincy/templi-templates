import { ApiProperty } from "@nestjs/swagger";
import { IsDateString, IsEnum, IsString, IsUUID } from "class-validator";
import { Role } from "src/model";

export class CreateUser {
  @IsUUID()
  @ApiProperty()
  id: string;

  @ApiProperty()
  @IsString()
  password: string;

  @ApiProperty()
  @IsString()
  username: string;

  @ApiProperty({ enum: Role })
  @IsEnum(Role)
  role: Role;

  @ApiProperty({ format: "date-time" })
  @IsDateString()
  createdAt: string;

  @ApiProperty({ format: "date-time" })
  @IsDateString()
  updatedAt: string;
}
