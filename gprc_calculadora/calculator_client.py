import grpc
import calculator_pb2
import calculator_pb2_grpc

def run():
    with grpc.insecure_channel('localhost:50051') as channel:
        stub = calculator_pb2_grpc.CalculatorStub(channel)

        num1 = calculator_pb2.ComplexNumber(real=3, imaginary=2)
        num2 = calculator_pb2.ComplexNumber(real=1, imaginary=7)

        response = stub.Calculate(calculator_pb2.OperationRequest(num1=num1, num2=num2, operation="add"))
        print(f"Addition result: {response.result.real} + {response.result.imaginary}i")

        # Você pode testar outras operações como "subtract", "multiply", e "divide".

if __name__ == '__main__':
    run()
