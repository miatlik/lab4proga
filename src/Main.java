import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

class Card {
    private int value; // Значение карты

    public Card(int val) {
        this.value = val;
    }

    public int getValue() {
        return value;
    }
}
class Deck {
    private static final int MAX_CARDS = 11; // Максимальное количество карт
    private int[] cards; // Массив для хранения значений карт
    private int size; // Текущий размер колоды
    private Random random;

    public Deck() {
        cards = new int[MAX_CARDS];
        random = new Random();
        vvodkolodi();
    }

    // Инициализация игральной колоды с очками от 1 до 11
    public void vvodkolodi() {
        for (int i = 0; i < MAX_CARDS; i++) {
            cards[i] = i + 1;
        }
        size = MAX_CARDS; // Инициализация размера колоды
    }
    // Случайный выбор карты из колоды
    public int viborkarti() {
        if (size <= 0) return -1; // Возвращаем невалидное значение
        int index = random.nextInt(size);
        int cardValue = cards[index];
        // Сдвиг оставшихся карт
        System.arraycopy(cards, index + 1, cards, index, size - index - 1);
        size--;
        return cardValue; // Возвращаем значение карты
    }
    // Размер колоды
    public int getSize() {
        return size;
    }
    // Возвращение максимального количества карт
    public static int getMaxCards() {
        return MAX_CARDS;
    }
}
class Player {
    private List<Card> hand; // Список для хранения карт

    public Player() {
        hand = new ArrayList<>();
    }
    // Получение карты в руку игрока
    public void ruka(int cardValue) {
        if (hand.size() < Deck.getMaxCards()) {
            hand.add(new Card(cardValue)); // Динамическое выделение карты
        }
    }
    // Вывод руки игрока
    public void myvivod() {
        int sum = 0;
        System.out.print("\nМои карты: ");
        for (Card card : hand) {
            System.out.print(card.getValue() + ", ");
            sum += card.getValue();
        }
        System.out.println("Сумма: " + sum + "/21");
    }
    // Вывод руки противника без первой карты
    public void opvivodhide() {
        int sum = 0;
        System.out.print("\nКарты противника: ?, ");
        for (int i = 1; i < hand.size(); i++) {
            System.out.print(hand.get(i).getValue() + ", ");
            sum += hand.get(i).getValue();
        }
        System.out.println("Сумма: ? + " + sum + "/21");
    }
    // Вывод полной руки противника
    public void opvivodopen() {
        int sum = 0;
        System.out.print("\nКарты противника: ");
        for (Card card : hand) {
            System.out.print(card.getValue() + ", ");
            sum += card.getValue();
        }
        System.out.println("Сумма: " + sum + "/21");
    }
    // Сумма очков
    public int getTotalValue() {
        int sum = 0;
        for (Card card : hand) {
            sum += card.getValue();
        }
        return sum;
    }
    // Вывод результата игры
    public void vivodrez(Player opponent) {
        int playerScore = this.getTotalValue();
        int opponentScore = opponent.getTotalValue();
        if (playerScore > 21 && opponentScore < 22) {
            System.out.println("\nУ вас перебор. Вы проиграли\n");
        } else if (opponentScore > 21 && playerScore < 22) {
            System.out.println("\nУ противника перебор. Вы выиграли\n");
        } else if (playerScore > 21 && opponentScore > 21) {
            if (playerScore > opponentScore) {
                System.out.println("\nУ вас и противника перебор. Вы проиграли, так как имеете больше очков\n");
            } else {
                System.out.println("\nУ вас и противника перебор. Вы выиграли, так как имеете меньше очков\n");
            }
        } else if (playerScore < 22 && opponentScore < 22) {
            if (playerScore > opponentScore) {
                System.out.println("\nВы выиграли. Вы ближе к 21 очку\n");
            } else if (playerScore < opponentScore) {
                System.out.println("\nВы проиграли. Противник ближе к 21 очку\n");
            } else {
                System.out.println("\nНичья\n");
            }
        }
    }
    // Простейший искусственный интеллект для противника, который берет карту, если у него меньше 17 очков
    public boolean reshenie_ai() {
        return this.getTotalValue() < 17; // Возвращаем true (взять карту) или false (остановиться)
    }
}
class BlackjackGame {
    private static final int MAX_CARDS = 11; // Максимальное количество карт
    private static Scanner scanner = new Scanner(System.in);
    private static Random random = new Random();
    public static void main(String[] args) {
        char ch;
        do {
            boolean playAgain1 = true;
            boolean playAgain2 = true;
            Deck deck = new Deck();
            Player player = new Player(); // Игрок
            Player opponent = new Player(); // Противник
            deck.vvodkolodi();
            // Начальная раздача карт
            player.ruka(deck.viborkarti());
            player.ruka(deck.viborkarti());
            opponent.ruka(deck.viborkarti());
            opponent.ruka(deck.viborkarti());
            // Основная игра
            while (playAgain1==true || playAgain2==true) {
                // Отображение карт
                player.myvivod();
                opponent.opvivodhide();
                //Выбор игрока
                if (playAgain1==true) {
                    System.out.println("\nНажмите 1, чтобы тянуть карту");
                    System.out.println("Нажмите 2, чтобы спасовать");
                    int choice = getPlayerChoice();

                    if (choice == 1 && player.getTotalValue() <= 21) {
                        player.ruka(deck.viborkarti());
                    }
                    else if(choice == 2 || player.getTotalValue() > 21){
                        System.out.println("Вы спасовали");
                        playAgain1=false;
                    }
                }
                // Выбор противника
                if(playAgain2==true) {
                    if (opponent.reshenie_ai()) {
                        opponent.ruka(deck.viborkarti());
                    } else {
                        System.out.println("\nПротивник спасовал");
                        playAgain2 = false;
                    }
                }
            }
            // Вывод результатов
            if (playAgain1==false && playAgain2==false) {
                player.myvivod(); // Полное отображение карт игрока
                opponent.opvivodopen(); // Полное отображение карт противника
                // Оценка игры
                player.vivodrez(opponent);
            }
            System.out.println("\nНажмите q, чтобы выйти или любую другую клавишу, чтобы сыграть заново");
            ch = scanner.nextLine().charAt(0);
        } while (ch != 'q');
    }
    //Обработка ошибок
    private static int getPlayerChoice() {
        int choice;
        while (true) {
            try {
                choice = Integer.parseInt(scanner.nextLine());
                if (choice < 1 || choice > 2) {
                    throw new NumberFormatException();
                }
                return choice;
            } catch (NumberFormatException e) {
                System.out.println("Ошибка. Выберите 1 или 2: ");
            }
        }
    }
}