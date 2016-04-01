/**
 * Contains the Systems and SubSystems working on Entities with specific Components
 * <p>
 * SubSystems are pure utility classes providing functionality for exactly one Component.
 * Component specific code that is used by various Systems should be placed in a SubSystem.
 * This must never add indirect System to System dependency ({@code Sys1 <-> SubSys <-> Sys2}),
 * but only one way {@code System -> Subsystem} dependency
 * <p>
 * Systems must implement the {@link com.koucha.experimentalgame.entitySystem.System} interface.
 * Ideally by extending {@link com.koucha.experimentalgame.entitySystem.system.AbstractSystem}
 *
 * @see com.koucha.experimentalgame.entitySystem.System
 * @see com.koucha.experimentalgame.entitySystem.Entity
 * @see com.koucha.experimentalgame.entitySystem.Component
 * @see com.koucha.experimentalgame.entitySystem.component
 */
package com.koucha.experimentalgame.entitySystem.system;