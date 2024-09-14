import grpc
from concurrent import futures
import calculator_pb2
import calculator_pb2_grpc

class CalculatorServicer(calculator_pb2_grpc.CalculatorServicer):

    def Calculate(self, request, context):
        num1 = request.num1
        num2 = request.num2
        operation = request.operation

        if operation == "add":
            result = self.add(num1, num2)
        elif operation == "subtract":
            result = self.subtract(num1, num2)
        elif operation == "multiply":
            result = self.multiply(num1, num2)
        elif operation == "divide":
            result = self.divide(num1, num2)
        else:
            context.set_code(grpc.StatusCode.INVALID_ARGUMENT)
            context.set_details("Invalid operation")
            return calculator_pb2.ComplexResponse()

        return calculator_pb2.ComplexResponse(result=result)

    def add(self, num1, num2):
        return calculator_pb2.ComplexNumber(
            real=num1.real + num2.real,
            imaginary=num1.imaginary + num2.imaginary
        )

    def subtract(self, num1, num2):
        return calculator_pb2.ComplexNumber(
            real=num1.real - num2.real,
            imaginary=num1.imaginary - num2.imaginary
        )

    def multiply(self, num1, num2):
        real = num1.real * num2.real - num1.imaginary * num2.imaginary
        imaginary = num1.imaginary * num2.real + num1.real * num2.imaginary
        return calculator_pb2.ComplexNumber(real=real, imaginary=imaginary)

    def divide(self, num1, num2):
        denom = num2.real ** 2 + num2.imaginary ** 2
        if denom == 0:
            raise ZeroDivisionError("Division by zero")
        real = (num1.real * num2.real + num1.imaginary * num2.imaginary) / denom
        imaginary = (num1.imaginary * num2.real - num1.real * num2.imaginary) / denom
        return calculator_pb2.ComplexNumber(real=real, imaginary=imaginary)

def serve():
    server = grpc.server(futures.ThreadPoolExecutor(max_workers=10))
    calculator_pb2_grpc.add_CalculatorServicer_to_server(CalculatorServicer(), server)
    server.add_insecure_port('[::]:50051')
    server.start()
    server.wait_for_termination()

if __name__ == '__main__':
    serve()
