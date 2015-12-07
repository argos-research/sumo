function maxSpeed = getMaxSpeed(vehID)
%getMaxSpeed Returns the maximum speed in m/s of this vehicle.
%   maxSpeed = getMaxSpeed(VEHID) Returns the maximum speed in m/s of this 
%   vehicle.

%   Copyright 2015 Universidad Nacional de Colombia,
%   Politecnico Jaime Isaza Cadavid.
%   Authors: Andres Acosta, Jairo Espinosa, Jorge Espinosa.
%   $Id$

import traci.constants
maxSpeed = traci.vehicle.getUniversal(constants.VAR_MAXSPEED, vehID);