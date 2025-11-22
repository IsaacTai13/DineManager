# DineManager

DineManager is a JavaFX-based restaurant management application that demonstrates how to design and implement custom Abstract Data Types (ADTs) and data structures from scratch, then apply them to real-world business logic and user interfaces.

The project is organized into three main functional views:

1. `view/CustomerOrderingPage.java` \- Customer-facing ordering interface  
2. `view/KitchenManagementPage.java` \- Kitchen order queue and processing  
3. `view/MenuManagementPage.java` \- Menu maintenance and management tools  

Under the hood, DineManager showcases:

- Custom ADT definitions in `adt/`  
- Concrete data structure implementations in `impl/`  
- Business logic and domain services in `service/`  
- JavaFX UI and controllers in `view/` and `controller/`  

The core goal is to illustrate how hand-crafted data structures drive behavior in a modular, layered application.

## Technologies

<p align="left">
  <!-- Programming Language -->
  <img src="https://img.shields.io/badge/Java-17-007396?logo=java&logoColor=white" height="22"/>

  <!-- GUI Framework -->
  <img src="https://img.shields.io/badge/JavaFX-UI%20Toolkit-0E6EB8?logo=java&logoColor=white" height="22"/>

  <!-- Data Structures -->
  <img src="https://img.shields.io/badge/Data%20Structures-Heap%20%7C%20BST%20%7C%20HashTable-4CAF50" height="22"/>

  <!-- Algorithms -->
  <img src="https://img.shields.io/badge/Algorithms-Priority%20Queue%20%7C%20Heapify%20%7C%20Merge%20Sort%20-9C27B0" height="22"/>
</p>

## Contributors
[![Hanson](https://img.shields.io/badge/GitHub-chs415009-black?logo=github)](https://github.com/chs415009)
[![Alvin](https://img.shields.io/badge/GitHub-linweihong--alvin-0366d6?logo=github&logoColor=white)](https://github.com/linweihong-alvin)
[![Isaac](https://img.shields.io/badge/GitHub-IsaacTai13-6f42c1?logo=github&logoColor=white)](https://github.com/IsaacTai13)


---

## Table of Contents

1. Overview
2. Project Structure
3. Core Data Structures & Rationale
4. Architecture
5. Business Logic Layer (`service/`)
6. User Interface (`view/` & `controller/`)
7. Getting Started

---

## 1. Overview

DineManager simulates core restaurant workflows:

- Customers browse the menu and place orders.
- Orders are prioritized and processed by the kitchen.
- Staff manage and update menu items.

The UI is built with JavaFX and connects to the service layer through controllers. Key emphasis: hand-crafted data structures drive business behavior.

---

## 2. Project Structure

Top-level directories and their purpose:

- `application/` \- JavaFX entry and global resources
- `view/` \- JavaFX page classes for the three main screens
- `controller/` \- Controllers mediating between UI and services
- `model/` \- Domain entities (menu items, orders)
- `service/` \- Business logic and service classes
- `adt/` \- ADT interfaces (contracts)
- `impl/` \- Concrete data structure implementations
- `test/` \- JUnit tests for ADTs and services

---

## 3. Core Data Structures & Rationale

This section explains the three primary custom structures used, how they are implemented, their time complexities, and why they were chosen.

### 3.1 `MyHashTable` (Hash Map)
- Implementation: fixed-size bucket array with separate chaining using a custom linked list implementation (`adt/LinkedListInterface.java` + `impl/MyLinkedList.java`). Buckets resize when the load factor threshold is crossed (e.g., 0.75). See `impl/MyHashTable.java` for details.
- Complexity: average O(1) for `put/get/remove`; worst-case O(n) if many collisions or poor hashing.
- Why: constant-time lookups for menu items by id/name improves UI responsiveness (search, edits). Using a hand-implemented linked list for buckets demonstrates both ADT layering and the trade-offs of separate chaining.

### 3.2 `HeapPriorityQueue` / `MinHeap` (Priority Queue)
- Implementation: binary heap stored in an array; comparator orders by numeric priority (lower = higher urgency), then by timestamp to preserve FIFO for equal priorities.
- Complexity: insertion O(log n), removal O(log n), peek O(1).
- Why: efficient retrieval of the next order to process. Min-heap guarantees the kitchen always sees the most urgent order with minimal overhead.

### 3.3 `BST<K,V>` (Binary Search Tree)
- Implementation: node-based binary tree for ordered keys, supports insert/search/traverse; can be balanced variant or plain BST (unbalanced).
- Complexity: average O(log n) for search/insert; worst-case O(n) if degenerate.
- Why: natural fit for ordered traversals (e.g., sorted menu by name or price), teaching differences between balanced vs unbalanced trees.

### 3.4 `Merge Sort` (Algorithm used for sorting extracted lists)
- Implementation: stable, divide-and-conquer merge sort (top-down or bottom-up) operating on arrays/lists with an auxiliary buffer for merging.
- Complexity: worst/average/best-case O(n log n) time; O(n) extra space for the merge buffer.
- Why: used by `MenuSortingService` when extracting items from ADTs (e.g., lists from BST or hash table) to produce reliably fast, stable sorted results (by name, price, category). Merge Sort provides consistent O(n log n) performance and stability (important when secondary order, like insertion time, must be preserved), making UI sorting predictable and efficient even for large menu lists.


Notes: Each structure is intentionally hand-implemented (not thin wrappers over Java collections) to illustrate algorithmic details and trade-offs.

---

## 4. Architecture

### 4.1 Layers and Packages

- **View Layer** (`view/`)
- **Controller Layer** (`controller/`)
- **Service / Business Logic Layer** (`service/`)
- **Domain Model Layer** (`model/`)
- **Data Structure Layer** (`adt/` and `impl/`)
- **Application Entry Point** (`application/Main.java`)

### 4.2 Data Flow

1. UI events captured in JavaFX views.
2. Controllers translate events into service calls.
3. Services manipulate domain objects using custom data structures.
4. Controllers update UI from service responses.

This separation keeps UI code simple while emphasizing the hand-built data structures in the service layer.

---

## 5. Business Logic Layer (`service/`)

This layer implements domain rules and coordinates the custom data structures from `adt/` and `impl/`. Implementation details and time complexities for underlying structures are documented in Section 3.

- `MenuService` — central API for menu operations. Responsibilities:
    - Add, update, remove, and query `MenuItem`s.
    - Keep `BST` and `MyHashTable` representations in sync for ordered views and fast lookups.
    - Provide search/filter helpers used by the UI.

- `OrderQueue` — domain-level queue for incoming orders.
    - Wraps the priority queue implementation (`HeapPriorityQueue`/`MinHeap`) but exposes domain methods: `add`, `poll`, `peek`, `size`, `clear`.
    - Encapsulates ordering rules (priority values, tie-breaking by timestamp) so controllers call simple domain methods without needing data-structure details. See Section 3 for heap implementation and complexity.

- `CookingManager` / `ProcessedOrder` — manage active cooking orders and finished history.
    - Enforce max concurrent cooking limit and transition orders between states: Waiting → Cooking → Finished/Cancelled.
    - Provide methods: `startOrder`, `finishOrder`, `cancelOrder`, `listActive`, `listHistory`.
    - Emit state-change notifications or return values used by controllers to refresh the UI.

- `InitialDataLoader` / `DataManager`
    - Seed initial menu and order state for the application runtime.
    - Provide centralized access to in-memory state used by services and controllers.

- `MenuSortingService`
    - Provide sorting utilities (by name, price, category) built on top of `BST` traversals or list-based algorithms extracted from data structures.

Notes:
- Keep business logic focused on domain rules and state transitions; avoid embedding low-level data-structure implementation details here. Controllers interact with concise service APIs while Section 3 documents the why/how of chosen data structures.

---

## 6. User Interface (`view/` & `controller/`)

The UI is implemented in JavaFX with dedicated pages and controllers.

### 6.1 Customer Ordering Page

- Browse menu, build orders, submit to `OrderQueue`.
- Uses `MenuService` for menu data and lookups.

### 6.2 Kitchen Management Page — concise logic summary

Purpose: provide a clear, real-time workflow for kitchen staff to process prioritized orders.

Key concepts (concise, non-verbatim):

- Stages: Waiting (priority queue) → Cooking (active orders) → Finished/Cancelled (history).
- Waiting orders are stored in a `MinHeap` priority queue; the UI displays waiting count.
- When a cook starts an order, it moves from Waiting into the Cooking list (active orders). The system enforces a maximum concurrent cooking limit (configurable, default e.g., 5).
- The detail panel always focuses on either the earliest Cooking order (if any) or previews the next Waiting order; when both lists are empty the UI clears and disables action buttons.
- Buttons behavior:
    - Start Next: enabled when Waiting not empty and Cooking count < max; moves next waiting order into Cooking.
    - Finish: enabled for the currently Cooking order; marks it finished and removes it from Cooking.
    - Cancel: works for either Waiting or Cooking; removes the order and triggers the same reload logic as Finish.
- Every action triggers a UI refresh: table update, detail panel update, waiting count, and button state recalculation to keep the UI consistent.

### 6.3 Menu Management Page

- Add, update, remove `MenuItem`s.
- Uses `MenuService` to keep `BST` and `MyHashTable` in sync.

---

## 7. Getting Started

### 7.1 Prerequisites

- Java 17 (or compatible)
- IntelliJ IDEA (or other Java IDE)
- JavaFX SDK (if not bundled)

### 7.2 Running

1. Import the project into IDE.
2. Configure JavaFX on module/classpath.
3. Run `application/Main.java`.