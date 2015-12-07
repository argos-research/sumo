function maxSpeed = getMaxSpeed(typeID)
%getMaxSpeed Returns the maximum speed in m/s of vehicles of this type.
%   maxSpeed = getMaxSpeed(TYPEID) Returns the maximum speed in m/s of 
%   vehicles of this type.

%   Copyright 2015 Universidad Nacional de Colombia,
%   Politecnico Jaime Isaza Cadavid.
%   Authors: Andres Acosta, Jairo Espinosa, Jorge Espinosa.
%   $Id$

import traci.constants
maxSpeed = traci.vehicletype.getUniversal(constants.VAR_MAXSPEED, typeID);