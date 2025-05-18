import { SetMetadata, UseGuards, applyDecorators } from "@nestjs/common";
import { ApiBearerAuth } from "@nestjs/swagger";

import { JwtAuthGuard, RoleGuard, SelfMatcherGuard } from "../guards";
import { Role } from "src/model";

export function Authenticated({
  roles = [],
  selfMatcher = "",
}: {
  roles?: Role[];
  selfMatcher?: string;
  selfRegionMatcher?: string;
} = {}) {
  return applyDecorators(
    SetMetadata("roles", roles),
    SetMetadata("self-matcher", selfMatcher),
    UseGuards(JwtAuthGuard, RoleGuard, SelfMatcherGuard),
    ApiBearerAuth()
  );
}
