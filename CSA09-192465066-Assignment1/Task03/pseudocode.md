# Task 3 pseudocode
```
ALGORITHM AllocateSlot(slots[][], vehicleNo)
BEGIN
  FOR f <- 0 TO LENGTH(slots)-1 DO
    FOR s <- 0 TO LENGTH(slots[f])-1 DO
      IF slots[f][s] = NULL THEN slots[f][s] <- vehicleNo; RETURN (f,s)
  RETURN NULL
END

ALGORITHM ComputeFee(minutes, rate, dailyMax)
BEGIN
  IF minutes < 0 THEN RAISE InvalidDuration
  hours <- (minutes + 59) DIV 60
  fee <- hours * rate
  IF fee > dailyMax THEN fee <- dailyMax
  RETURN fee
END
```
