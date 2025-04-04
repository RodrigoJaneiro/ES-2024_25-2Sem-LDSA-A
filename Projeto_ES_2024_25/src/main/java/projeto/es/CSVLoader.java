package projeto.es;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.locationtech.jts.io.ParseException;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class CSVLoader {
    public static List<Propriedade> LoadPropriedades(String nomeFicheiroCsv) throws IOException, ParseException {
        final int MAX_LINHAS = 14083;
        int i = 0;

        String caminhoCsv = Objects.requireNonNull(App.class.getClassLoader().getResource(nomeFicheiroCsv + ".csv"))
                .getPath();
        List<Propriedade> properties = new ArrayList<>();

        try (FileReader reader = new FileReader(caminhoCsv);

                CSVParser csvParser = CSVFormat.DEFAULT.builder()
                        .setDelimiter(';')
                        .setHeader()
                        .setSkipHeaderRecord(true)
                        .build()
                        .parse(reader)) {
            for (CSVRecord record : csvParser) {
                if (i == MAX_LINHAS) {
                    break;
                } else {
                    i++;
                }

                properties.add(new Propriedade(record));
            }
        }
        return properties;
    }
}