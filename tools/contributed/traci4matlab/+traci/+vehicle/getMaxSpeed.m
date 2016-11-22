function maxSpeed = getMaxSpeed(vehID)
%getMaxSpeed Returns the maximum speed in m/s of this vehicle.
%   maxSpeed = getMaxSpeed(VEHID) Returns the maximum speed in m/s of this 
%   vehicle.

%   Copyright 2016 Universidad Nacional de Colombia,
%   Politecnico Jaime Isaza Cadavid.
%   Authors: Andres Acosta, Jairo Espinosa, Jorge Espinosa.
%   $Id: getMaxSpeed.m 31 2016-09-28 15:16:56Z afacostag $

import traci.constants
maxSpeed = traci.vehicle.getUniversal(constants.VAR_MAXSPEED, vehID);