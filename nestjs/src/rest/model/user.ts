import { ApiProperty } from "@nestjs/swagger";
import { Role } from "src/model";

export class User {
  @ApiProperty({ format: "uuid" })
  id: string;

  @ApiProperty()
  username: string;

  @ApiProperty({ enum: Role })
  role: Role;

  @ApiProperty({ format: "date-time" })
  createdAt: string;

  @ApiProperty({ format: "date-time" })
  updatedAt: string;
}
