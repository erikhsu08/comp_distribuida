import pygame
import random
import math

# Inicialização do Pygame
pygame.init()
screen = pygame.display.set_mode((800, 600))
pygame.display.set_caption("Asteroids")

# Configurações globais
font = pygame.font.SysFont(None, 36)

class Player:
    def __init__(self):
        self.x = 400
        self.y = 300
        self.angle = 0
        self.speed = 0
        self.radius = 15

    def move(self):
        keys = pygame.key.get_pressed()
        if keys[pygame.K_LEFT]:
            self.angle += 5
        if keys[pygame.K_RIGHT]:
            self.angle -= 5
        if keys[pygame.K_UP]:
            self.speed = 5
        elif keys[pygame.K_DOWN]:
            self.speed = -3
        else:
            self.speed = 0

        self.x += self.speed * math.cos(math.radians(self.angle))
        self.y -= self.speed * math.sin(math.radians(self.angle))

        # Loop de bordas
        self.x %= 800
        self.y %= 600

    def draw(self):
        points = [
            (self.x + 15 * math.cos(math.radians(self.angle)), self.y - 15 * math.sin(math.radians(self.angle))),
            (self.x + 15 * math.cos(math.radians(self.angle + 120)), self.y - 15 * math.sin(math.radians(self.angle + 120))),
            (self.x + 15 * math.cos(math.radians(self.angle + 240)), self.y - 15 * math.sin(math.radians(self.angle + 240)))
        ]
        pygame.draw.polygon(screen, (255, 255, 255), points)

class Bullet:
    def __init__(self, x, y, angle):
        self.x = x
        self.y = y
        self.angle = angle

    def move(self):
        self.x += 10 * math.cos(math.radians(self.angle))
        self.y -= 10 * math.sin(math.radians(self.angle))

    def is_off_screen(self):
        return not (0 < self.x < 800 and 0 < self.y < 600)

    def draw(self):
        pygame.draw.circle(screen, (255, 255, 255), (int(self.x), int(self.y)), 3)

class Asteroid:
    def __init__(self, x, y, size):
        self.x = x
        self.y = y
        self.size = size
        self.angle = random.randint(0, 360)
        self.speed = random.random() * 2 + 1

    def move(self):
        self.x += self.speed * math.cos(math.radians(self.angle))
        self.y -= self.speed * math.sin(math.radians(self.angle))

        # Loop de bordas
        self.x %= 800
        self.y %= 600

    def draw(self):
        pygame.draw.circle(screen, (255, 255, 255), (int(self.x), int(self.y)), self.size)

    def check_collision(self, player):
        distance = math.hypot(player.x - self.x, player.y - self.y)
        return distance < (player.radius + self.size)

class Game:
    def __init__(self):
        self.player = Player()
        self.bullets = []
        self.asteroids = []
        self.score = 0
        self.running = True
        self.create_asteroids()

    def create_asteroids(self):
        while len(self.asteroids) < 5:
            x = random.randint(0, 800)
            y = random.randint(0, 600)
            size = random.randint(20, 50)
            self.asteroids.append(Asteroid(x, y, size))

    def check_collisions(self):
        for bullet in self.bullets[:]:
            for asteroid in self.asteroids[:]:
                if math.hypot(bullet.x - asteroid.x, bullet.y - asteroid.y) < asteroid.size:
                    self.asteroids.remove(asteroid)
                    self.bullets.remove(bullet)
                    self.score += 10
                    break

        for asteroid in self.asteroids:
            if asteroid.check_collision(self.player):
                self.running = False  # Finaliza o jogo em caso de colisão

    def run(self):
        while self.running:
            screen.fill((0, 0, 0))
            for event in pygame.event.get():
                if event.type == pygame.QUIT:
                    self.running = False

            self.player.move()
            if pygame.key.get_pressed()[pygame.K_SPACE]:
                self.bullets.append(Bullet(self.player.x, self.player.y, self.player.angle))

            for bullet in self.bullets[:]:
                bullet.move()
                if bullet.is_off_screen():
                    self.bullets.remove(bullet)

            for asteroid in self.asteroids:
                asteroid.move()

            self.check_collisions()

            self.player.draw()
            for bullet in self.bullets:
                bullet.draw()
            for asteroid in self.asteroids:
                asteroid.draw()

            score_text = font.render(f'Score: {self.score}', True, (255, 255, 255))
            screen.blit(score_text, (10, 10))

            pygame.display.flip()
            pygame.time.delay(30)

        pygame.quit()

# Executando o jogo
if __name__ == "__main__":
    Game().run()
