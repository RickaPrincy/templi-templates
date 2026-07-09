import request from "supertest";
import { createTestApp, TestApp } from "./utils/application";
import { DUMMY_1, DUMMY_2, DUMMY_3 } from "../mocks";

describe("AppController (e2e)", () => {
  let testApp: TestApp;

  beforeAll(async () => {
    testApp = await createTestApp();
  });

  it("/ping (GET)", async () => {
    const res = await request(testApp.app.getHttpServer()).get("/ping");

    expect(res.text).toBe("pong");
  });

  it("/dummies (GET)", async () => {
    const res = await request(testApp.app.getHttpServer()).get("/dummies");

    expect(res.body).toStrictEqual([DUMMY_1, DUMMY_2, DUMMY_3]);
  });

  afterAll(async () => {
    await testApp.close();
  });
});
