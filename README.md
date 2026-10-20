# Voter Registration & Polling Place Lookup

A Python application that registers voters, validates their information,
and looks up the polling place assigned to their district.

## How to Run

No external packages are required — everything uses the Python standard
library (Python 3.8+).

**Console version:**
```
python3 main.py
```

**GUI version (Tkinter):**
```
python3 gui.py
```
> Tkinter ships with the standard Python installer on Windows and macOS.
> On some Linux distributions it must be installed separately
> (`sudo apt install python3-tk`). The console version (`main.py`) has no
> such dependency and will run anywhere.

**Unit tests:**
```
python3 -m unittest discover -s tests -v
```

Voter data is stored in `data/voters.txt` and persists between runs of
either `main.py` or `gui.py` (they share the same registry/data file).

## Project Files

| File                    | Purpose                                                              |
|--------------------------|-----------------------------------------------------------------------|
| `voter.py`               | `Voter` class and `AbsenteeVoter` subclass (validation, OOP core)   |
| `polling_places.py`      | Dictionary of polling places keyed by district + lookup helpers     |
| `registry.py`            | `VoterRegistry` class: registration, lookup, file I/O                |
| `main.py`                | Console driver (menu loop, input validation)                        |
| `gui.py`                 | Tkinter GUI driver (extension)                                      |
| `tests/test_voter.py`    | Unit tests for `Voter` / `AbsenteeVoter`                             |
| `tests/test_registry.py` | Unit tests for `VoterRegistry`, including file save/load round-trip |
| `data/voters.txt`        | Persisted voter roll (created automatically on first save)          |

## How Required Concepts Were Met

**Core Python concepts**
- Multiple functions/methods — every module; instance methods on `Voter`/`VoterRegistry`
- User input with validation — `main.py` (`prompt_nonempty`, `prompt_district`, `prompt_yes_no` re-prompt until valid)
- Program output — console output (`main.py`), GUI output (`gui.py`), and file output (`data/voters.txt`)
- Decision structures — `if`/`elif`/`else` throughout (menu routing, validation, absentee branching)
- Looping — `while True` menu loop, `for` loops over voters/districts/file lines
- File I/O — `VoterRegistry.save()` / `.load()` read and write `data/voters.txt`
- Collections — `list` (`self.voters`), `dict` (`POLLING_PLACES`), `set` (`Voter.VALID_DISTRICTS`)

**Object-Oriented Programming**
- Classes: `Voter`, `AbsenteeVoter`, `VoterRegistry`
- Constructors: `__init__` on `Voter`, `AbsenteeVoter`, `VoterRegistry`
- Instance methods: `summary()`, `to_record()`, `register_voter()`, `find_voter()`, etc.
- Object creation/use: voters are instantiated in `main.py`/`gui.py` and passed into the registry
- Appropriate design: validation lives on `Voter`, persistence lives on `VoterRegistry`, data lives in `polling_places.py`

**Software practices**
- Exception handling: custom `InvalidVoterDataError` / `DuplicateVoterError`, `try`/`except` around validation, registration, and file I/O
- Unit testing: 18 tests across `tests/test_voter.py` and `tests/test_registry.py` (`unittest`)
- Multi-file organization: separate modules (`voter.py`, `polling_places.py`, `registry.py`) imported by two drivers (`main.py`, `gui.py`)

## Extension Requirements (3+ required, 5 implemented)

1. **GUI component** — `gui.py` (Tkinter registration form + lookup panel)
2. **`*args`/`**kwargs`** — `polling_places.all_polling_places(*args, **kwargs)`
3. **Dictionary** — `POLLING_PLACES` in `polling_places.py`
4. **`datetime`** — `Voter.registered_on = datetime.now()`, formatted in `summary()`
5. **Inheritance/polymorphism** — `AbsenteeVoter(Voter)` overrides `summary()` and `to_record()`

## Sample Data

The app ships with 5 sample districts/polling places pre-loaded in
`polling_places.py`. The voter roll (`data/voters.txt`) starts empty and
fills up as voters are registered through either interface.
