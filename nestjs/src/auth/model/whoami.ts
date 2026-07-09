import { User } from "@nestapp/rest/model";
import { ApiProperty } from "@nestjs/swagger";

export class Whoami extends User {
  @ApiProperty()
  token: string;
}
