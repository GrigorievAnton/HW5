import java.util.HashMap;
import java.util.Map;

public class HomeWork5 {

    /*
   �������� ������� #5:
   !!! � ���� ����� ������� ��������� ����������� ������� private

   ����������� ���������� �PersonDead�
       ����������� ����� �� �Exception�

   ����������� ����� �Person�
       ���� ������ - name:String, protection:Integer, health:Integer (+�����������, +�������)
       ��� ���� ����� 50 �������� � ������ 0
       � ������ Person ������ ���� ��� ������������
            - protected ����������� - ��� ���� �������� ����������� ������������
            - public ����������� - �������� ������ ���, ��������� �������� �� ���������
       ����� - String announce() {} - ���������� ������ � ������� "Person /name/ ����� ��������������: /health/ �������� � /protection/ ������"
       ����� - void takeDamage(Integer damage) {} - �������� ���� �� �������� ��������� �� ������� health - (damage - protection)
            ���� �� ����� ���� ������������� (�������� ���������� Exception)
            �������� �� ����� ����� �������������
            ����� �������� ��������� ���������� �� 0, ������������ ���������� PersonDead
       ����� - Integer facePunch() - ���� � ���� ������� ���� ����

   ����������� ����� �Mage�
       ����������� ����� �� �Person�
       ����������� ������ ��������� ������ ���
       ��� ���� ����� 100 �������� � ������ 15
       �����(��������������) announce - ���������� ������ � ������� Mage + ����� ������ announce � ��������
       �����(��������������) takeDamage - �������� ���� �� �������� ���������
            �� ������� health - (damage - protection - health % 10) (��������� ���)
            !!! ����� ������ �������� ����� takeDamage super-������
       ����� - Integer fireBall() - ������� ������� 45 �����

   ����������� ����� �Archer�
       ����������� ����� �� �Person�
       ����������� ������ ��������� ������ ���
       ��� ������� ����� 120 �������� � ������ 12
       �����(��������������) announce - ���������� ������ � ������� Archer + ����� ������ announce � ��������
       �����(��������������) takeDamage - �������� ���� �� �������� ���������
            �� ������� health - (damage - protection + health % 10) (��������� �����)
            !!! ����� ������ �������� ����� takeDamage super-������
       ����� - Integer shootBow() - �������� �� ���� ������� 40 + health % 10 �����
    */

    public static class PersonDead extends Exception {
        public PersonDead(String message) {
            super(message);
        }
    }

    public static class Person {
        public String getName() {
            return name;
        }

        public int getRotection() {
            return protection;
        }

        public void setHealth(int health) {
            this.health = health;
        }

        public int getHealth() {
            return health;
        }

        // ������ ����� ���� ���������� ������ Person
        private String name;

        public Person(String name) {
            this.name = name;
            this.protection = 0;
            this.health = 50;
        }

        protected Person(String name, int protection, int health) {
            this.name = name;
            this.protection = protection;
            this.health = health;
        }

        public int getProtection() {
            return protection;
        }

        private int protection;
        private int health;

        String announce() {
            return "Person " + name + " ����� ��������������: " + health + " �������� � " + protection + " ������";
        }

        void takeDamage(Integer damage) throws Exception {
            if (damage - protection < 0) {
                throw new Exception();
            } else {
                this.health -= (damage - protection);
                if (this.health < 0) {
                    this.health = 0;
                    throw new PersonDead("You dead");
                }
            }
        }
        Integer facePunch() {
            int damage = 1;
            return damage;
        }
    }

    public static class Mage extends Person{
        // ������ ����� ���� ���������� ������ Mage (�� ������ ��� ������������ �� Person)
        public Mage(String name) {
            super(name, 15, 100);
        }
        @Override
        String announce() {
            return "Mage" + super.announce();
        }
        @Override
        void takeDamage(Integer damage) throws Exception {
            damage -= this.getHealth() % 10;
            super.takeDamage(damage);
        }
        Integer fireBall() {
            int damage = 45;
            return damage;
        }
    }

    public static class Archer extends Person{
        // ������ ����� ���� ���������� ������ Archer (�� ������ ��� ������������ �� Person)
        public Archer(String name) {
            super(name, 12, 120);
        }
        @Override
        String announce() {
            return "Archer" + super.announce();
        }
        @Override
        void takeDamage(Integer damage) throws Exception {
            damage += this.getHealth() % 10;
            super.takeDamage(damage);
        }
            Integer shootBow () {
                int damage = 40 + this.getHealth() % 10;
                return damage;
            }
    }

    /*
   ��� ����� main - ����� play ��� �� ��������� �����.
   ������ �� ����� � ������, ��� ��� �������� ��� ��� �� ��������� ���� �������.
   ��������� ������ � ����� ���������� ������� - ��� ������ ��� ���������� �� ����� �� �����.
   ���� ���������� �� �����������, ���� �� ��� �� ��������, ����� �� ����������.
   */
    public static void main(String[] args) throws Exception {
        ANSIColor color = new ANSIColor();
        boolean firstCheck;
        boolean secondCheck;
        boolean thirdCheck = false;

        Person person = new Person(PERSON_NAME);
        System.out.println(color.type("green", "Tests for Person"));
        test("Person: ����� 50 �������� � 0 ������", person.getHealth() == 50 && person.getProtection() == 0);
        test("Person: announce() �������� ���", person.announce().contains(PERSON_NAME));
        test("Person: announce() �������� ��������", person.announce().contains(PERSON_HEALTH.toString()));
        test("Person: announce() �������� ������", person.announce().contains(PERSON_PROTECTION.toString()));
        test("Person: facePunch() ������� 1 ����", person.facePunch() == 1);
        try {
            person.takeDamage(DAMAGE1);
            test("Person: takeDamage() ���� ����������� ���������", person.getHealth() == 20);
            person.takeDamage(DAMAGE1);
        } catch (PersonDead e) {
            test("Person: takeDamage() �������� �� ���������� ������ ����", person.getHealth() == 0);
            test("Person: ����� PersonDead()", true);
        }

        Mage mage = new Mage(MAGE_NAME);
        System.out.println(color.type("green", "Tests for Mage"));
        test("Mage: ����������� �� Person", mage instanceof Person);
        test("Mage: ����� 100 �������� � 15 ������", mage.getHealth() == 100 && mage.getProtection() == 15);
        test("Mage: announce() �������� ���", mage.announce().contains(MAGE_NAME));
        test("Mage: announce() �������� ��������", mage.announce().contains(MAGE_HEALTH.toString()));
        test("Mage: announce() �������� ������", mage.announce().contains(MAGE_HEALTH.toString()));
        test("Mage: fireBall() ������� 45 �����", mage.fireBall() == 45);
        mage.takeDamage(DAMAGE1);
        firstCheck = mage.getHealth() == 85;
        mage.takeDamage(DAMAGE1);
        secondCheck = mage.getHealth() == 75;
        test("Mage: takeDamage() ��������� �� �������", firstCheck && secondCheck);
        try {
            mage.takeDamage(DAMAGE2);
        } catch (PersonDead e) {
            thirdCheck = true;
        }
        test("Mage: ����� ���������� PersonDead()", thirdCheck);

        thirdCheck = false;
        Archer archer = new Archer(ARCHER_NAME);
        System.out.println(color.type("green", "Tests for Archer"));
        test("Archer: ����������� �� Person", archer instanceof Person);
        test("Archer: ����� 120 �������� � 12 ������", archer.getHealth() == 120 && archer.getProtection() == 12);
        test("Archer: announce() �������� ���", archer.announce().contains(ARCHER_NAME));
        test("Archer: announce() �������� ��������", archer.announce().contains(ARCHER_HEALTH.toString()));
        test("Archer: announce() �������� ������", archer.announce().contains(ARCHER_PROTECTION.toString()));
        test("Archer: shootBow() ��������� �� �������", archer.shootBow() == 40 + archer.getHealth() % 10);
        archer.takeDamage(DAMAGE1);
        firstCheck = archer.getHealth() == 102;
        archer.takeDamage(DAMAGE1);
        test("Archer: shootBow() ��������� �� �������", archer.shootBow() == 40 + archer.getHealth() % 10);
        secondCheck = archer.getHealth() == 82;
        try {
            archer.takeDamage(DAMAGE2);
        } catch (PersonDead e) {
            thirdCheck = true;
        }
        test("Archer: takeDamage() ��������� �� �������", firstCheck && secondCheck);
        test("Archer: ����� PersonDead()", thirdCheck);
    }

    /* ����������� ������ - ���� ������ ������ �� ���� */

    private static void test(String expression, Boolean condition) {
        ANSIColor color = new ANSIColor();
        System.out.print("TEST CASE " + color.type("yellow+bold", normalOutput(expression, 60)));
        if (condition)
            System.out.println("?");
        else
            System.out.println("?");
    }

    private static String normalOutput(String str, int len) {
        StringBuilder sb = new StringBuilder(str);
        while ((len--) - str.length() > 0)
            sb.append(" ");
        return sb.toString();
    }

    private final static String PERSON_NAME = "NamePerson";
    private final static String MAGE_NAME = "NameMage";
    private final static String ARCHER_NAME = "NameArcher";
    private final static Integer PERSON_HEALTH = 50;
    private final static Integer PERSON_PROTECTION = 0;
    private final static Integer MAGE_HEALTH = 100;
    private final static Integer MAGE_PROTECTION = 15;
    private final static Integer ARCHER_HEALTH = 120;
    private final static Integer ARCHER_PROTECTION = 12;
    private final static Integer DAMAGE1 = 30;
    private final static Integer DAMAGE2 = 1000;

    private static class ANSIColor {
        public Map<String, String> ansiColors = new HashMap<>();
        {
            ansiColors.put("reset", "\u001B[0m");
            ansiColors.put("black", "\u001B[30m");
            ansiColors.put("red", "\u001B[31m");
            ansiColors.put("green", "\u001B[32m");
            ansiColors.put("light_yellow", "\u001B[93m");
            ansiColors.put("yellow", "\u001B[33m");
            ansiColors.put("yellow_background", "\u001B[43m");
            ansiColors.put("blue", "\u001B[34m");
            ansiColors.put("purple", "\u001B[35m");
            ansiColors.put("cyan", "\u001B[36m");
            ansiColors.put("white", "\u001B[37m");
            ansiColors.put("bold", "\u001B[1m");
            ansiColors.put("stop_bold", "\u001B[21m");
            ansiColors.put("underground", "\u001B[4m");
            ansiColors.put("stop_underground", "\u001B[24m");
            ansiColors.put("blink", "\u001B[5m");
        }

        public String type(String color, String message) {
            String[] colors = color.split("\\+");
            StringBuilder sb = new StringBuilder();
            for (String colorr : colors) {
                if (ansiColors.get(colorr.toLowerCase()) == null)
                    throw new RuntimeException("Unknown ANSI color: " + colorr);
                sb.append(ansiColors.get(colorr.toLowerCase()));
            }
            return sb.toString() + message + ansiColors.get("reset");
        }
    }
}