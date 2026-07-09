import { GenericContainer } from "testcontainers";

const DB_PORT = 5432;
export const createPostgresContainer = async () => {
  const container = new GenericContainer("postgres")
    .withExposedPorts(DB_PORT)
    .withEnvironment({
      POSTGRES_USER: "test",
      POSTGRES_PASSWORD: "test",
      POSTGRES_DB: "test_db",
    });

  const startedContainer = await container.start();

  return {
    container,
    startedContainer,
    dbUrl: `postgres://test:test@${startedContainer.getHost()}:${startedContainer.getMappedPort(DB_PORT)}/test_db`,
  };
};
