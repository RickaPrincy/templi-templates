import request from "supertest";
import { createTestApp, TestApp } from "./utils/application";
import { DUMMY_1, DUMMY_2, DUMMY_3 } from "../mocks";

describe("AuthController (e2e)", () => {
  let testApp: TestApp;

  beforeAll(async () => {
    testApp = await createTestApp();
  });

  it("ping should be excludes from any verification", async () => {
    const res = await request(testApp.app.getHttpServer()).get("/ping");

    expect(res.text).toBe("pong");
  });

  it("public /dummies should passes if token is not present", async () => {
    const res = await request(testApp.app.getHttpServer()).get("/dummies");

    expect(res.body).toStrictEqual([DUMMY_1, DUMMY_2, DUMMY_3]);
  });

  afterAll(async () => {
    await testApp.close();
  });
});
