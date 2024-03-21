include "base.thrift"
namespace java com.bytedance.thrift.example

struct ExampleRequest {
    1: string Name,

    255: optional base.Base Base,
}

struct ExampleResponse {
    1: string Resp,

    255: base.BaseResp BaseResp,
}

service ExampleService {
    ExampleResponse Visit(1: ExampleRequest req)
}
