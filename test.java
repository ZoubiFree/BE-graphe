public boolean isValid() {
    // Empty path is valid
    if (isEmpty()) {
        return true;
    }
    // Single node without arcs is valid
    if (arcs.isEmpty()) {
        return true;
    }
    // Check if the first arc has the origin of the path
    if (arcs.get(0).getOrigin() != origin) {
        return false;
    }
    // Check consecutive arcs
    for (int i = 0; i < arcs.size() - 1; i++) {
        Arc currentArc = arcs.get(i);
        Arc nextArc = arcs.get(i + 1);
        if (currentArc.getDestination() != nextArc.getOrigin()) {
            return false;
        }
    }
    return true;
}