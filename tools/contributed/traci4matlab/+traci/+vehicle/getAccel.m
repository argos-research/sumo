function accel = getAccel(vehID)
%getAccel Get the acceleration of the vehicle.
%   accel = getAccel(VEHID) Returns the maximum acceleration possibility in
%   m/s^2 of this vehicle.

%   Copyright 2016 Universidad Nacional de Colombia,
%   Politecnico Jaime Isaza Cadavid.
%   Authors: Andres Acosta, Jairo Espinosa, Jorge Espinosa.
%   $Id: getAccel.m 31 2016-09-28 15:16:56Z afacostag $

import traci.constants
accel = traci.vehicle.getUniversal(constants.VAR_ACCEL, vehID);