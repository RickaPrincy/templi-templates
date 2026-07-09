import type { Config } from "jest";

const config: Config = {
  moduleFileExtensions: ["js", "json", "ts"],
  rootDir: ".",
  testRegex: ".*\\.e2e\\.spec\\.ts$",
  setupFilesAfterEnv: ["<rootDir>/../../jest.setup.ts"],
  transform: {
    "^.+\\.(t|j)s$": "ts-jest",
  },
  testEnvironment: "node",
  transformIgnorePatterns: [
    "node_modules/(?!uuid)",
  ],
  moduleNameMapper: {
    "^@nestapp/auth$": "<rootDir>/../../src/auth",
    "^@nestapp/rest$": "<rootDir>/../../src/rest",
    "^@nestapp/entities$": "<rootDir>/../../src/entities",
    "^@nestapp/services$": "<rootDir>/../../src/services",
    "^@nestapp/modules$": "<rootDir>/../../src/modules",
    "^@nestapp/auth/(.*)$": "<rootDir>/../../src/auth/$1",
    "^@nestapp/rest/(.*)$": "<rootDir>/../../src/rest/$1",
    "^@nestapp/entities/(.*)$": "<rootDir>/../../src/entities/$1",
    "^@nestapp/services/(.*)$": "<rootDir>/../../src/services/$1",
    "^@nestapp/modules/(.*)$": "<rootDir>/../../src/modules/$1",
  },
};

export default config;
