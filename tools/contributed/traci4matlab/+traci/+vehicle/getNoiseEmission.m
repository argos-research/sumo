function noiseEmission = getNoiseEmission(vehID)
%getNoiseEmission Get the noise emission of the vehicle.
%   noiseEmission = getNoiseEmission(VEHID) Returns the noise emission in 
%   db for the last time step.

%   Copyright 2016 Universidad Nacional de Colombia,
%   Politecnico Jaime Isaza Cadavid.
%   Authors: Andres Acosta, Jairo Espinosa, Jorge Espinosa.
%   $Id: getNoiseEmission.m 31 2016-09-28 15:16:56Z afacostag $

import traci.constants
noiseEmission = traci.vehicle.getUniversal(constants.VAR_NOISEEMISSION, vehID);