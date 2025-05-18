import { ApiProperty } from "@nestjs/swagger";
import { User } from "src/rest/model";

export class Whoami extends User {
  @ApiProperty()
  token: string;
}
