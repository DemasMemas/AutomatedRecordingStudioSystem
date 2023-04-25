package zh.arss.service;

import zh.arss.database.DatabaseHandler;
import zh.arss.entity.Arrangement;
import zh.arss.entity.Request;
import zh.arss.entity.User;
import zh.arss.utilities.PasswordHasher;

import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.util.List;
import java.util.Random;

public class MainService {
    private static final MainService MAIN_SERVICE = new MainService();

    private MainService() {
    }

    public static MainService getInstance() {
        return MAIN_SERVICE;
    }

    private final DatabaseHandler databaseHandler = DatabaseHandler.getInstance();
    private final String WAV_LICENSE_TEXT = """
            License Agreement
            (EXCLUSIVE RIGHTS)
            Sound Recording/BEATS
            THIS LICENCE AGREEMENT is made on Saturday 18th of February 2023 ("Effective Date") by and between NAME (hereinafter referred to as the "Licensee") also, if applicable, professionally known as NAME, whose principle address is ADDRESS, United States of America, and Valentin Zhukov (hereinafter referred to as the "Licensor") also, if applicable, professionally known as YM, whose principle address is Moscovskaya 15, Penza, Russia. Licensor warrants that it controls the mechanical rights in and to the copyrighted musical works entitled TRACKNAME  ("Composition") as of and prior to the date first written above. The Composition, including the music thereof, was composed by Valentin Zhukov ("Songwriter") managed under the Licensor.
            Master Use. The Licensor hereby grants to License an exclusive license (this "License") to record vocal synchronization to the Composition partly or in its entirety and substantially in its original form ("Master Recording").
            Mechanical Rights. The Licensor hereby grants to Licensee an exclusive license to use Master Recording in the reproduction, duplication, manufacture, and distribution of phonograph records, cassette tapes, compact disk, digital downloads, other miscellaneous audio and digital recordings, and any lifts and versions thereof (collectively, the "Recordings", and individually, a "Recordings") worldwide for unlimited copies of such Recordings or any combination of such Recordings, condition upon the payment to the Licensor a sum of $20.06 US Dollars, receipt of which is confirmed.
            Performance Rights. The Licensor here by grants to Licensee an exclusive license to use the Master Recording in unlimited for-profit performances, shows, or concerts.
            Broadcast Rights. The Licensor hereby grants to Licensee an exclusive licence to broadcast or air the Master Recording in unlimited amounts of radio stations.
            Credit. Licensee shall acknowledge the original authorship of the Composition appropriately and reasonably in all media and performance formats under the name "YM" in writing where possible and vocally otherwise.
            Consideration. In consideration for the rights granted under this agreement, Licensee shall pay to licensor the sum of $20.06 US dollars and other good and valuable consideration, payable to "Valentin Zhukov", receipt of which is hereby acknowledged. If the Licensee fails to account to the Licensor, timely complete the payments provided for hereunder, or perform its other obligations hereunder, including having insufficient bank balance, the licensor shall have the right to terminate License upon written notice to the Licensee. Such termination shall render the recording, manufacture and/or distribution of Recordings for which monies have not been paid subject to and actionable infringements under applicable law, including, without limitation, the United States Copyright Act, as amended.
            Delivery. The Composition shall be delivered via email to an email address that Licensee provided when making their payment to Licensor. Licensee shall receive an email containing an attachment or link from which they can download the Composition.
            Indemnification. Accordingly, Licensee agrees to indemnify and hold Licensor harmless from and against any and all claims, losses, damages, costs, expenses, including, without limitation, reasonable attorney's fees, arising of or resulting from a claimed breach of any of Licensee's representations, warranties or agreements hereunder.
            Audio Samples. 3rd party sample clearance is the responsibility of the Licensee.
            Miscellaneous. This license is non-transferable and is limited to the Composition specified above, constitutes the entire agreement between the Licensor and the Licensee relating to the Composition, and shall be binding upon both the Licensor and the Licensee and their respective successors, assigns, and legal representatives.
            Governing Law. This License is governed by and shall be construed under the law of the State of Penza, Russia, without regard to the conflicts of laws principles thereof.
            Publishing.
            NAME, owns 90% of publishing rights.
            Valentin Zhukov, owns 10% of publishing rights.
            Finished audio recording by Licensee of audio release can distribute to music supervisors for consideration of synchronization licensing. Only the recording artist or recording company can monetize with this license. This is not a synchronization license for music supervisors of the TV, Film and Video game industry.
            THE PARTIES HAVE DULY EXECUTED THIS AGREEMENT on the date first written above.
            Licensor:
            _______________________________________ Date: ______________, 20__
            Valentin Zhukov - Producer
            Authorized Signing Officer
            Licensee:
            _______________________________________ Date: ______________, 20__
            NAME - Artist
            Authorized Signing Officer
                        
                        
            """;

    private final String TRACK_OUT_LICENSE_TEXT = """
            License Agreement\s
            (EXCLUSIVE RIGHTS)
            Sound Recording/BEATS
            THIS LICENCE AGREEMENT is made on Saturday 18th of February 2023 ("Effective Date") by and between NAME (hereinafter referred to as the "Licensee") also, if applicable, professionally known as NAME, whose principle address is ADDRESS, United States of America, and Valentin Zhukov (hereinafter referred to as the "Licensor") also, if applicable, professionally known as YM, whose principle address is Moscovskaya 15, Penza, Russia. Licensor warrants that it controls the mechanical rights in and to the copyrighted musical works entitled TRACKNAME ("Composition") as of and prior to the date first written above. The Composition, including the music thereof, was composed by Valentin Zhukov ("Songwriter") managed under the Licensor.
            Master Use. The Licensor hereby grants to License an exclusive license (this "License") to record vocal synchronization to the Composition partly or in its entirety and substantially in its original form ("Master Recording").
            Mechanical Rights. The Licensor hereby grants to Licensee an exclusive license to use Master Recording in the reproduction, duplication, manufacture, and distribution of phonograph records, cassette tapes, compact disk, digital downloads, other miscellaneous audio and digital recordings, and any lifts and versions thereof (collectively, the "Recordings", and individually, a "Recordings") worldwide for unlimited copies of such Recordings or any combination of such Recordings, condition upon the payment to the Licensor a sum of $40.13 US Dollars, receipt of which is confirmed.
            Performance Rights. The Licensor here by grants to Licensee an exclusive license to use the Master Recording in unlimited for-profit performances, shows, or concerts.
            Broadcast Rights. The Licensor hereby grants to Licensee an exclusive licence to broadcast or air the Master Recording in unlimited amounts of radio stations.
            Credit. Licensee shall acknowledge the original authorship of the Composition appropriately and reasonably in all media and performance formats under the name "YM" in writing where possible and vocally otherwise.
            Consideration. In consideration for the rights granted under this agreement, Licensee shall pay to licensor the sum of $40.13 US dollars and other good and valuable consideration, payable to "Valentin Zhukov", receipt of which is hereby acknowledged. If the Licensee fails to account to the Licensor, timely complete the payments provided for hereunder, or perform its other obligations hereunder, including having insufficient bank balance, the licensor shall have the right to terminate License upon written notice to the Licensee. Such termination shall render the recording, manufacture and/or distribution of Recordings for which monies have not been paid subject to and actionable infringements under applicable law, including, without limitation, the United States Copyright Act, as amended.
            Delivery. The Composition shall be delivered via email to an email address that Licensee provided when making their payment to Licensor. Licensee shall receive an email containing an attachment or link from which they can download the Composition.
            Indemnification. Accordingly, Licensee agrees to indemnify and hold Licensor harmless from and against any and all claims, losses, damages, costs, expenses, including, without limitation, reasonable attorney's fees, arising of or resulting from a claimed breach of any of Licensee's representations, warranties or agreements hereunder.
            Audio Samples. 3rd party sample clearance is the responsibility of the Licensee.
            Miscellaneous. This license is non-transferable and is limited to the Composition specified above, constitutes the entire agreement between the Licensor and the Licensee relating to the Composition, and shall be binding upon both the Licensor and the Licensee and their respective successors, assigns, and legal representatives.
            Governing Law. This License is governed by and shall be construed under the law of the State of Penza, Russia, without regard to the conflicts of laws principles thereof.
            Publishing.
            NAME, owns 100% of publishing rights.
            Valentin Zhukov, owns 0% of publishing rights.
            Finished audio recording by Licensee of audio release can distribute to music supervisors for consideration of synchronization licensing. Only the recording artist or recording company can monetize with this license. This is not a synchronization license for music supervisors of the TV, Film and Video game industry.
            THE PARTIES HAVE DULY EXECUTED THIS AGREEMENT on the date first written above.
            Licensor:
            _______________________________________ Date: ______________, 20__
            Valentin Zhukov - Producer
            Authorized Signing Officer
            Licensee:
            _______________________________________ Date: ______________, 20__
            NAME - Artist
            Authorized Signing Officer
                        
                        
            """;

    private final String MP3_LICENSE_TEXT = """
            License Agreement
            (EXCLUSIVE RIGHTS)
            Sound Recording/BEATS
            THIS LICENCE AGREEMENT is made on Saturday 18th of February 2023 ("Effective Date") by and between NAME (hereinafter referred to as the "Licensee") also, if applicable, professionally known as NAME, whose principle address is NAME, United States of America, and Valentin Zhukov (hereinafter referred to as the "Licensor") also, if applicable, professionally known as YM, whose principle address is Moscovskaya 15, Penza, Russia. Licensor warrants that it controls the mechanical rights in and to the copyrighted musical works entitled TRACKNAME ("Composition") as of and prior to the date first written above. The Composition, including the music thereof, was composed by Valentin Zhukov ("Songwriter") managed under the Licensor.
            Master Use. The Licensor hereby grants to License an exclusive license (this "License") to record vocal synchronization to the Composition partly or in its entirety and substantially in its original form ("Master Recording").
            Mechanical Rights. The Licensor hereby grants to Licensee an exclusive license to use Master Recording in the reproduction, duplication, manufacture, and distribution of phonograph records, cassette tapes, compact disk, digital downloads, other miscellaneous audio and digital recordings, and any lifts and versions thereof (collectively, the "Recordings", and individually, a "Recordings") worldwide for unlimited copies of such Recordings or any combination of such Recordings, condition upon the payment to the Licensor a sum of $10.70 US Dollars, receipt of which is confirmed.
            Performance Rights. The Licensor here by grants to Licensee an exclusive license to use the Master Recording in unlimited for-profit performances, shows, or concerts.
            Broadcast Rights. The Licensor hereby grants to Licensee an exclusive licence to broadcast or air the Master Recording in unlimited amounts of radio stations.
            Credit. Licensee shall acknowledge the original authorship of the Composition appropriately and reasonably in all media and performance formats under the name "YM" in writing where possible and vocally otherwise.
            Consideration. In consideration for the rights granted under this agreement, Licensee shall pay to licensor the sum of $10.70 US dollars and other good and valuable consideration, payable to "Valentin Zhukov", receipt of which is hereby acknowledged. If the Licensee fails to account to the Licensor, timely complete the payments provided for hereunder, or perform its other obligations hereunder, including having insufficient bank balance, the licensor shall have the right to terminate License upon written notice to the Licensee. Such termination shall render the recording, manufacture and/or distribution of Recordings for which monies have not been paid subject to and actionable infringements under applicable law, including, without limitation, the United States Copyright Act, as amended.
            Delivery. The Composition shall be delivered via email to an email address that Licensee provided when making their payment to Licensor. Licensee shall receive an email containing an attachment or link from which they can download the Composition.
            Indemnification. Accordingly, Licensee agrees to indemnify and hold Licensor harmless from and against any and all claims, losses, damages, costs, expenses, including, without limitation, reasonable attorney's fees, arising of or resulting from a claimed breach of any of Licensee's representations, warranties or agreements hereunder.
            Audio Samples. 3rd party sample clearance is the responsibility of the Licensee.
            Miscellaneous. This license is non-transferable and is limited to the Composition specified above, constitutes the entire agreement between the Licensor and the Licensee relating to the Composition, and shall be binding upon both the Licensor and the Licensee and their respective successors, assigns, and legal representatives.
            Governing Law. This License is governed by and shall be construed under the law of the State of Penza, Russia, without regard to the conflicts of laws principles thereof.
            Publishing.
            NAME, owns 80% of publishing rights.
            Valentin Zhukov, owns 20% of publishing rights.
            Finished audio recording by Licensee of audio release can distribute to music supervisors for consideration of synchronization licensing. Only the recording artist or recording company can monetize with this license. This is not a synchronization license for music supervisors of the TV, Film and Video game industry.
            THE PARTIES HAVE DULY EXECUTED THIS AGREEMENT on the date first written above.
            Licensor:
            _______________________________________ Date: ______________, 20__
            Valentin Zhukov - Producer
            Authorized Signing Officer
            Licensee:
            _______________________________________ Date: ______________, 20__
            NAME - Artist
            Authorized Signing Officer
                        
                        
            """;

    private final String UNLIMITED_LICENSE_TEXT = """
            License Agreement
            (EXCLUSIVE RIGHTS)
            Sound Recording/BEATS
            THIS LICENCE AGREEMENT is made on Saturday 18th of February 2023 ("Effective Date") by and between NAME (hereinafter referred to as the "Licensee") also, if applicable, professionally known as NAME, whose principle address is ADDRESS, United States of America, and Valentin Zhukov (hereinafter referred to as the "Licensor") also, if applicable, professionally known as YM, whose principle address is Moscovskaya 15, Penza, Russia. Licensor warrants that it controls the mechanical rights in and to the copyrighted musical works entitled TRACKNAME ("Composition") as of and prior to the date first written above. The Composition, including the music thereof, was composed by Valentin Zhukov ("Songwriter") managed under the Licensor.
            Master Use. The Licensor hereby grants to License an exclusive license (this "License") to record vocal synchronization to the Composition partly or in its entirety and substantially in its original form ("Master Recording").
            Mechanical Rights. The Licensor hereby grants to Licensee an exclusive license to use Master Recording in the reproduction, duplication, manufacture, and distribution of phonograph records, cassette tapes, compact disk, digital downloads, other miscellaneous audio and digital recordings, and any lifts and versions thereof (collectively, the "Recordings", and individually, a "Recordings") worldwide for unlimited copies of such Recordings or any combination of such Recordings, condition upon the payment to the Licensor a sum of $26.75 US Dollars, receipt of which is confirmed.
            Performance Rights. The Licensor here by grants to Licensee an exclusive license to use the Master Recording in unlimited for-profit performances, shows, or concerts.
            Broadcast Rights. The Licensor hereby grants to Licensee an exclusive licence to broadcast or air the Master Recording in unlimited amounts of radio stations.
            Credit. Licensee shall acknowledge the original authorship of the Composition appropriately and reasonably in all media and performance formats under the name "YM" in writing where possible and vocally otherwise.
            Consideration. In consideration for the rights granted under this agreement, Licensee shall pay to licensor the sum of $26.75 US dollars and other good and valuable consideration, payable to "Valentin Zhukov", receipt of which is hereby acknowledged. If the Licensee fails to account to the Licensor, timely complete the payments provided for hereunder, or perform its other obligations hereunder, including having insufficient bank balance, the licensor shall have the right to terminate License upon written notice to the Licensee. Such termination shall render the recording, manufacture and/or distribution of Recordings for which monies have not been paid subject to and actionable infringements under applicable law, including, without limitation, the United States Copyright Act, as amended.
            Delivery. The Composition shall be delivered via email to an email address that Licensee provided when making their payment to Licensor. Licensee shall receive an email containing an attachment or link from which they can download the Composition.
            Indemnification. Accordingly, Licensee agrees to indemnify and hold Licensor harmless from and against any and all claims, losses, damages, costs, expenses, including, without limitation, reasonable attorney's fees, arising of or resulting from a claimed breach of any of Licensee's representations, warranties or agreements hereunder.
            Audio Samples. 3rd party sample clearance is the responsibility of the Licensee.
            Miscellaneous. This license is non-transferable and is limited to the Composition specified above, constitutes the entire agreement between the Licensor and the Licensee relating to the Composition, and shall be binding upon both the Licensor and the Licensee and their respective successors, assigns, and legal representatives.
            Governing Law. This License is governed by and shall be construed under the law of the State of Penza, Russia, without regard to the conflicts of laws principles thereof.
            Publishing.
            NAME, owns 100% of publishing rights.
            Valentin Zhukov, owns 0% of publishing rights.
            Finished audio recording by Licensee of audio release can distribute to music supervisors for consideration of synchronization licensing. Only the recording artist or recording company can monetize with this license. This is not a synchronization license for music supervisors of the TV, Film and Video game industry.
            THE PARTIES HAVE DULY EXECUTED THIS AGREEMENT on the date first written above.
            Licensor:
            _______________________________________ Date: ______________, 20__
            Valentin Zhukov - Producer
            Authorized Signing Officer
            Licensee:
            _______________________________________ Date: ______________, 20__
            NAME - Artist
            Authorized Signing Officer
                        
                        
            """;

    private User user;

    public String authorization(String login, String password) {
        try {
            if (login.equals(""))
                return "Введите логин";
            if (password.equals(""))
                return "Введите пароль";

            password = PasswordHasher.hashingPassword(password);
            String response = databaseHandler.authorization(login, password);
            if (response.equals("login not found")) {
                return "Пользователь не найден";
            }
            if (response.equals("incorrect password")) {
                return "Неверный пароль";
            }

            user = databaseHandler.getUser(Long.parseLong(response));
            if (user.getIsAdministrator()) {
                return "admin";
            }

            return "success";
        } catch (SQLException e) {
            return "Неизвестная ошибка";
        }
    }

    public String registration(String login, String password) {
        if (login.equals(""))
            return "Введите логин";
        if (login.length() < 5)
            return "Логин короче 5 символов";
        if (password.equals(""))
            return "Введите пароль";
        if (password.length() < 5)
            return "Пароль короче 5 символов";

        password = PasswordHasher.hashingPassword(password);

        String response = databaseHandler.registration(login, password);
        if (response.equals("error")) {
            return "Ошибка регистрации";
        }
        return response;
    }

    public String createRequest(String date, String email, String phone, String description) {
        if (email.equals("")) {
            return "Ошибка";
        }
        if (phone.equals("")) {
            return "Ошибка";
        }

        date = date.split(": ")[1];
        String service;
        if (date.split(" ")[1].equals("ночь")) {
            service = "Аренда";
        } else {
            service = "Запись";
        }
        StringBuilder code = new StringBuilder();
        for (int i = 0; i < 6; i++) {
            code.append(new Random().nextInt(9));
        }

        try {
            databaseHandler.insertRequest(user.getIdUser(), date, service, code.toString(), email, phone, description);
            return code.toString();
        } catch (Exception exception) {
            return "Ошибка";
        }
    }

    public User getUser() {
        return user;
    }

    public boolean buyArrangement(Arrangement arrangement, String license){
        try {
            license = license.substring(license.indexOf('\'') + 1, license.length() - 3);
            databaseHandler.buyArrangement(arrangement.getIdArrangement());
            String fileName = (user.getLogin() + "_" + arrangement.getName() + "_" + license + ".txt")
                    .replace(" ", "_").replace(":", "_")
                    .replace(",", "_").replace("\"", "_")
                    .replace("?", "_").replace("|", "_");
            PrintWriter writer = new PrintWriter(fileName, StandardCharsets.UTF_8);

            String licenseText = "";

            switch (license.substring(0, license.indexOf('-'))) {
                case "WAV " -> {
                    licenseText = WAV_LICENSE_TEXT;
                    licenseText = licenseText.replaceAll("TRACKNAME", arrangement.getName());
                    licenseText = licenseText.replaceAll("NAME", user.getLogin());
                }
                case "TRACK OUT " -> {
                    licenseText = TRACK_OUT_LICENSE_TEXT;
                    licenseText = licenseText.replaceAll("TRACKNAME", arrangement.getName());
                    licenseText = licenseText.replaceAll("NAME", user.getLogin());
                }
                case "MP3 Lease " -> {
                    licenseText = MP3_LICENSE_TEXT;
                    licenseText = licenseText.replaceAll("TRACKNAME", arrangement.getName());
                    licenseText = licenseText.replaceAll("NAME", user.getLogin());
                }
                case "WAV Unlimit " -> {
                    licenseText = UNLIMITED_LICENSE_TEXT;
                    licenseText = licenseText.replaceAll("TRACKNAME", arrangement.getName());
                    licenseText = licenseText.replaceAll("NAME", user.getLogin());
                }
            }

            writer.println(licenseText);
            writer.close();

            Files.move(Paths.get(fileName), Paths.get( "licenses\\" + fileName));

            databaseHandler.insertPurchase(user.getIdUser(), arrangement.getIdArrangement());
            return true;
        } catch (Exception exception) {
            exception.printStackTrace();
            return false;
        }
    }

    public List<Request> getAllRequest() {
        return databaseHandler.getAllRequest();
    }
    public List<Arrangement> getAllArrangement() {
        return databaseHandler.getAllArrangement();
    }
}
