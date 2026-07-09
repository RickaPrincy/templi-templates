import type { Config } from 'jest';

const config: Config = {
  moduleFileExtensions: ['js', 'json', 'ts'],
  rootDir: '.',
  testMatch: ['<rootDir>/test/**/*.spec.ts'],
  transform: {
    '^.+\\.(t|j)s$': 'ts-jest',
  },
  collectCoverageFrom: ['src/**/*.(t|j)s'],
  coverageDirectory: 'coverage',
  testEnvironment: 'node',
  setupFilesAfterEnv: ["<rootDir>/jest.setup.ts"],
  moduleNameMapper: {
    "^@nestapp/auth$": "<rootDir>/src/auth",
    "^@nestapp/rest$": "<rootDir>/src/rest",
    "^@nestapp/entities$": "<rootDir>/src/entities",
    "^@nestapp/services$": "<rootDir>/src/services",
    "^@nestapp/modules$": "<rootDir>/src/modules",
    "^@nestapp/auth/(.*)$": "<rootDir>/src/auth/$1",
    "^@nestapp/rest/(.*)$": "<rootDir>/src/rest/$1",
    "^@nestapp/entities/(.*)$": "<rootDir>/src/entities/$1",
    "^@nestapp/services/(.*)$": "<rootDir>/src/services/$1",
    "^@nestapp/modules/(.*)$": "<rootDir>/src/modules/$1"
  }
};

export default config;