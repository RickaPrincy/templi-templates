import { PaginationParams } from "@nestapp/rest/decorator";
import { createPagination } from "@nestapp/services/common/create-pagination";

describe("createPagination", () => {
  it("should return correct take and skip for given page and pageSize", () => {
    const pagination: PaginationParams = { page: 2, pageSize: 10 };
    const result = createPagination(pagination);

    expect(result).toEqual({
      take: 10,
      skip: 10, // (2 - 1) * 10
    });
  });

  it("should return skip = 0 when page = 1", () => {
    const pagination: PaginationParams = { page: 1, pageSize: 25 };
    const result = createPagination(pagination);

    expect(result).toEqual({
      take: 25,
      skip: 0,
    });
  });

  it("should handle pageSize = 0", () => {
    const pagination: PaginationParams = { page: 3, pageSize: 0 };
    const result = createPagination(pagination);

    expect(result).toEqual({
      take: 0,
      skip: 0, // (3 - 1) * 0
    });
  });
});
