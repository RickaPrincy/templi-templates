import { Role, User } from "@nestapp/entities";

export const JOHN: Partial<User> = {
  id: "john_id",
  username: "John Doe",
  password: "john12345678",
  role: Role.ADMIN,
  createdAt: new Date().toISOString(),
  updatedAt: new Date().toISOString(),
};

export const JANE: Partial<User> = {
  id: "jane_id",
  username: "Jane Dane",
  password: "jane12345678",
  role: Role.SIMPLE_USER,
  createdAt: new Date().toISOString(),
  updatedAt: new Date().toISOString(),
};
