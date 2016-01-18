function noiseEmission = getNoiseEmission(vehID)
%getNoiseEmission Get the noise emission of the vehicle.
%   noiseEmission = getNoiseEmission(VEHID) Returns the noise emission in 
%   db for the last time step.

%   Copyright 2015 Universidad Nacional de Colombia,
%   Politecnico Jaime Isaza Cadavid.
%   Authors: Andres Acosta, Jairo Espinosa, Jorge Espinosa.
%   $Id: getNoiseEmission.m 20 2015-03-02 16:52:32Z afacostag $

import traci.constants
noiseEmission = traci.vehicle.getUniversal(constants.VAR_NOISEEMISSION, vehID);