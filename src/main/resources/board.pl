% Check if near a position there is an element K
% Input
% I: rowIndex, J: colIndex, K: value looking for
% Output
% X: rowIndex, Y: colIndex
near(I, J, X, Y, K) :- X is I - 1, Y is J - 1, cell(X, Y, K).
near(I, J, X, Y, K) :- X is I, Y is J - 1, cell(X, Y, K).
near(I, J, X, Y, K) :- X is I + 1, Y is J - 1, cell(X, Y, K).
near(I, J, X, Y, K) :- X is I - 1, Y is J, cell(X, Y, K).
near(I, J, X, Y, K) :- X is I + 1, Y is J, cell(X, Y, K).
near(I, J, X, Y, K) :- X is I - 1, Y is J + 1, cell(X, Y, K).
near(I, J, X, Y, K) :- X is I, Y is J + 1, cell(X, Y, K).
near(I, J, X, Y, K) :- X is I + 1, Y is J + 1, cell(X, Y, K).

is_present(I, J, [K|[]]) :- near(I, J, X, Y, K).
is_present(I, J, [K|T]) :- near(I, J, X, Y, K), is_present(X, Y, T).

% Check if a list contains near values
is_present([K|T]) :- cell(I, J, K), is_present(I, J, T), !.

