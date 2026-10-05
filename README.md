# Emaze Java Game

A legacy Java game project built with libGDX. The repository contains the core gameplay code for a multi-screen 2D game, including player movement, collision handling, level loading, UI state, preferences, assets, and screen transitions.

## Highlights

- Java/libGDX game architecture
- Multiple application screens including loading, splash, main menu, gameplay, settings, instructions, and game-over states
- Custom collision logic for players, discs, arcs, segments, planes, and level boundaries
- Level and map loading components
- Scene2D-based user interface and on-screen controls
- Persistent user preferences and settings
- Asset and sound management

## Project Structure

The main source code is under:

```text
core/src/com/macofugames/balldeveloper/
```

Key areas include:

- `actors/` - player and game entities
- `levelreader/` - level-data processing
- `maps/` - level representation
- `screens/` - major application screens
- `stages/` - gameplay UI/background stages
- `util/` - collisions, assets, constants, helpers, and preferences

## Technical Notes

This is an older project and reflects an earlier stage of my software-development work. It is retained as a code sample demonstrating hands-on Java development, object-oriented design, game-state management, and custom gameplay logic.

The repository contains the core module rather than a complete modern libGDX multi-platform build. Build outputs and local IDE metadata are intentionally excluded from version control.
