JavaFX KeyEvent keycodes

I pressed my keyboard's keys from top to bottom, left to right.

event.getCode() returns a KeyCode enum value with these names, which makes comparison efficient (don't want to compare Strings)

Note that LEFT and RIGHT are not here because those keys don't send events. Seems like they always change slider values instead of sending events.

ESCAPE

DIGIT1

DIGIT2

DIGIT3

DIGIT4

DIGIT5

DIGIT6

DIGIT7

DIGIT8

DIGIT9

DIGIT0

MINUS

EQUALS

BACK_SPACE

DELETE

TAB

Q

W

E

R

T

Y

U

I

O

P

OPEN_BRACKET

CLOSE_BRACKET

BACK_SLASH

HOME

CAPS

A

S

D

F

G

H

J

K

L

SEMICOLON

QUOTE

ENTER

PAGE_UP

SHIFT

Z

X

C

V

B

N

M

COMMA

PERIOD

SLASH

SHIFT

UP

PAGE_DOWN

CONTROL

ALT

COMMAND

SPACE

COMMAND

DOWN