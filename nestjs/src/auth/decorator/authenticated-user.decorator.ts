import { createParamDecorator, ExecutionContext } from "@nestjs/common";

import { User } from "@nestapp/entities";

export const AuthenticatedUser = createParamDecorator(
  (_data: unknown, context: ExecutionContext): User => {
    const request = context.switchToHttp().getRequest();
    return request.user;
  }
);
