package com.ai.mcpserver.tool;



import com.ai.mcpserver.service.CalculatorService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class CalculatorTool {

    private final CalculatorService calculatorService;

    @Tool(description = "Adds two numbers")
    public double add(double a, double b) {
        System.out.println("******** REMOTE MCP SERVER TOOL ********");
        return calculatorService.add(a, b);
    }

    @Tool(description = "Subtracts two numbers")
    public double subtract(double a, double b) {
        log.info("CalculatorTool.subtract({}, {})", a, b);
        return calculatorService.subtract(a, b);
    }

    @Tool(description = "Multiplies two numbers")
    public double multiply(double a, double b) {
        log.info("CalculatorTool.multiply({}, {})", a, b);
        return calculatorService.multiply(a, b);
    }

    @Tool(description = "Divides two numbers")
    public double divide(double a, double b) {
        log.info("CalculatorTool.divide({}, {})", a, b);
        return calculatorService.divide(a, b);
    }
}
