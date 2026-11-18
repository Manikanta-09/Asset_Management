-- 1. Rename department column in employees table
ALTER TABLE employees
    RENAME COLUMN deaparment_id TO department_id;

-- 2. Rename asset_assignments table to assignments
RENAME TABLE asset_assignments TO asset_assignments;

