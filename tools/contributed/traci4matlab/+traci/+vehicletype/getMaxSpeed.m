function maxSpeed = getMaxSpeed(typeID)
%getMaxSpeed Returns the maximum speed in m/s of vehicles of this type.
%   maxSpeed = getMaxSpeed(TYPEID) Returns the maximum speed in m/s of 
%   vehicles of this type.

%   Copyright 2016 Universidad Nacional de Colombia,
%   Politecnico Jaime Isaza Cadavid.
%   Authors: Andres Acosta, Jairo Espinosa, Jorge Espinosa.
%   $Id: getMaxSpeed.m 31 2016-09-28 15:16:56Z afacostag $

import traci.constants
maxSpeed = traci.vehicletype.getUniversal(constants.VAR_MAXSPEED, typeID);