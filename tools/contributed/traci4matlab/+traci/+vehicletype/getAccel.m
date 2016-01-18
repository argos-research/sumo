function accel = getAccel(typeID)
%getAccel Returns the maximum acceleration of vehicles of this type.
%   accel = getAccel(TYPEID) Returns the maximum acceleration in m/s^2 of 
%   vehicles of this type.

%   Copyright 2015 Universidad Nacional de Colombia,
%   Politecnico Jaime Isaza Cadavid.
%   Authors: Andres Acosta, Jairo Espinosa, Jorge Espinosa.
%   $Id: getAccel.m 22 2015-04-13 14:20:57Z afacostag $

import traci.constants
accel = traci.vehicletype.getUniversal(constants.VAR_ACCEL, typeID);