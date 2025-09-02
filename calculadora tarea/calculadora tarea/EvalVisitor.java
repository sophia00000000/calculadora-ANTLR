import java.util.HashMap;
import java.util.Map;

public class EvalVisitor extends LabeledExprBaseVisitor<Double> {
    /** "memory" for our calculator; variable/value pairs go here */
    Map<String, Double> memory = new HashMap<String, Double>();

    /** flag global para el modo (true = radianes, false = grados) */
    private boolean useRadians = false; // por defecto en grados


    @Override
    public Double visitSetModeGrados(LabeledExprParser.SetModeGradosContext ctx) {
        useRadians = false;
        System.out.println("Modo cambiado a GRADOS");
        return 0.0;
    }

    @Override
    public Double visitSetModeRadianes(LabeledExprParser.SetModeRadianesContext ctx) {
        useRadians = true;
        System.out.println("Modo cambiado a RADIANES");
        return 0.0;
    }


    /** ID '=' expr NEWLINE */
    @Override
    public Double visitAssign(LabeledExprParser.AssignContext ctx) {
        String id = ctx.ID().getText();  // id is left-hand side of '='
        Double value = visit(ctx.expr());   // compute value of expression on right
        memory.put(id, value);           // store it in our memory
        return value;
    }

    /** expr NEWLINE */
    @Override
    public Double visitPrintExpr(LabeledExprParser.PrintExprContext ctx) {
        Double value = visit(ctx.expr()); // evaluate the expr child
        System.out.println(value);         // print the result
        return 0.0;                          // return dummy value
    }


    /** ID */
    @Override
    public Double visitId(LabeledExprParser.IdContext ctx) {
        String id = ctx.ID().getText();
        if ( memory.containsKey(id) ) return memory.get(id);
        return 0.0;
    }

    /** expr op=('*'|'/') expr */
    @Override
    public Double visitMulDiv(LabeledExprParser.MulDivContext ctx) {
        Double left = visit(ctx.expr(0));  // get value of left subexpression
        Double right = visit(ctx.expr(1)); // get value of right subexpression
        if ( ctx.op.getType() == LabeledExprParser.MUL ) return left * right;
        return left / right; // must be DIV
    }

    /** expr op=('+'|'-') expr */
    @Override
    public Double visitAddSub(LabeledExprParser.AddSubContext ctx) {
        Double left = visit(ctx.expr(0));  // get value of left subexpression
        Double right = visit(ctx.expr(1)); // get value of right subexpression
        if ( ctx.op.getType() == LabeledExprParser.ADD ) return left + right;
        return left - right; // must be SUB
    }

    /** '(' expr ')' */
    @Override
    public Double visitParens(LabeledExprParser.ParensContext ctx) {
        return visit(ctx.expr()); // return child expr's value
    }

    @Override
    public Double visitNumber(LabeledExprParser.NumberContext ctx) {
        return Double.valueOf(ctx.NUMBER().getText());
    }

    @Override
    public Double visitPower(LabeledExprParser.PowerContext ctx) {
        return Math.pow(visit(ctx.expr(0)), visit(ctx.expr(1)));
    }

    @Override
    public Double visitNegate(LabeledExprParser.NegateContext ctx) {
        return -visit(ctx.expr());
    }

    @Override
    public Double visitFactorial(LabeledExprParser.FactorialContext ctx) {
        double val = visit(ctx.expr());
        return factorial((Double) val);
    }

    private double factorial(Double n) {
        if (n < 0) throw new RuntimeException("Factorial de número negativo");
        double result = 1;
        for (int i = 2; i <= n; i++) result *= i;
        return result;
    }

    @Override
    public Double visitFuncCall(LabeledExprParser.FuncCallContext ctx) {
        String f = ctx.func().getText();
        double value = visit(ctx.expr());

        //convertir a rad si está en grados
        if (!useRadians && (f.equals("sin") || f.equals("cos") || f.equals("tan"))) {
            value = Math.toRadians(value);
        }

        switch(f) {
            case "sin": return Math.sin(value); // en radianes
            case "cos": return Math.cos(value);
            case "tan": return Math.tan(value);
            case "sqrt": return Math.sqrt(value);
            case "ln": return Math.log(value); // log natural
            case "log": return Math.log10(value); // log base 10
            default: throw new RuntimeException("Función desconocida: " + f);
        }
    }



}