import { ApiProperty } from "@nestjs/swagger";
import { IsString } from "class-validator";

export class SigninPayload {
  @ApiProperty()
  @IsString()
  password: string;

  @ApiProperty()
  @IsString()
  username: string;
}
