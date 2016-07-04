package com.brait.explorer.runner;

import com.brait.explorer.environment.FunctionEnvironment3D;
import com.brait.explorer.genetics.Chromossome;
import net.objecthunter.exp4j.Expression;
import net.objecthunter.exp4j.ExpressionBuilder;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.lang3.StringUtils;

import java.util.Random;

/**
 * Created by andre on 04/07/16.
 */
public class GeneticsRunner {

    public static final String fn_name = "fn", from_name = "from", to_name = "to", step_name = "step",
            min_name = "min", n_name = "n", m_name = "m", c_name = "c", mode_name = "mode", ngen_name = "ngen";

    private final static Random rand = new Random();

    public static int randMinusOne() {
        return (int) Math.pow(-1.0, (double) rand.nextInt(10));
    }

    public static void run(CommandLine cmd) {
        String fn = StringUtils.deleteWhitespace(cmd.getOptionValue(fn_name));
        String[] from = cmd.getOptionValues(from_name);
        String[] to = cmd.getOptionValues(to_name);
        String stepStr = StringUtils.deleteWhitespace(cmd.getOptionValue(step_name));
        int ngen = cmd.hasOption(ngen_name) ? Integer.parseInt(cmd.getOptionValue(ngen_name)) : 1000;
        int n = cmd.hasOption(n_name) ? Integer.parseInt(cmd.getOptionValue(n_name)) : 100;
        double m = cmd.hasOption(m_name) ? Double.parseDouble(cmd.getOptionValue(m_name)) : 0.05;
        double c = cmd.hasOption(c_name) ? Double.parseDouble(cmd.getOptionValue(c_name)) : 0.60;
        boolean min = cmd.hasOption(min_name);
        int mode = cmd.hasOption(mode_name) && cmd.getOptionValue(mode_name).equals("random") ? FunctionEnvironment3D.MODE_RANDOM : FunctionEnvironment3D.MODE_UNIFORM;

        Expression function = new ExpressionBuilder(fn).variables("x", "y").build();

        double[] xLimits = new double[]{Double.parseDouble(from[0]), Double.parseDouble(to[0])};
        double[] yLimits = new double[]{Double.parseDouble(from[1]), Double.parseDouble(to[1])};
        double step = Double.parseDouble(stepStr);

        FunctionEnvironment3D environment = new FunctionEnvironment3D(function);
        environment.init(xLimits, yLimits, step);
        int[][] indexes = environment.generateIndexes(n, mode);

        Chromossome[] initialPopulation = new Chromossome[n];
        for (int i = 0; i < n; i++) {
            initialPopulation[i] = new Chromossome(indexes[i][0], indexes[i][1], new int[]{randMinusOne() * rand.nextInt(10), randMinusOne() * rand.nextInt(10)});
        }

        for (int gen = 0; gen < ngen; gen++) {

        }
    }

}