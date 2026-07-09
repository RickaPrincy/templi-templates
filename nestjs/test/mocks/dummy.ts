import { v4 as uuid } from "uuid";
import { Dummy } from "@nestapp/entities";

export const DUMMY_1: Dummy = {
  id: uuid(),
  name: "dummy1",
};

export const DUMMY_2: Dummy = {
  id: uuid(),
  name: "dummy2",
};

export const DUMMY_3: Dummy = {
  id: uuid(),
  name: "dummy3",
};
