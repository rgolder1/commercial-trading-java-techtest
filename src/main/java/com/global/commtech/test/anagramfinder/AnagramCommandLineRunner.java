package com.global.commtech.test.anagramfinder;

import java.io.File;

import com.global.commtech.test.anagramfinder.service.AnagramService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

@Component
@RequiredArgsConstructor
public class AnagramCommandLineRunner implements CommandLineRunner {

    @Autowired
    private AnagramService anagramService;

    @Override
    public void run(final String... args) {
        Assert.isTrue(args.length == 1, "Please ensure that the input file is provided");

        final File file = new File(args[0]);
        Assert.isTrue(file.exists(), args[0] + " Does not exist");

        anagramService.process(file);
    }
}
